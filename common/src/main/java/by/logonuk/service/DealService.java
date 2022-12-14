package by.logonuk.service;

import by.logonuk.domain.Car;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import by.logonuk.domain.attachments.TechnicalInfo;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.CarRepository;
import by.logonuk.repository.DealRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.service.mailsender.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final DealRepository dealRepository;

    private final ArchiveService archiveService;

    private final MailSenderService mailSender;

    @Transactional
    public Map<String, Object> validateDeal(String userIdRequest, String carIdRequest) {
        Long userId = Long.parseLong(userIdRequest);
        Long carId = Long.parseLong(carIdRequest);

        Optional<User> searchUser = userRepository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        Optional<Car> searchCar = carRepository.findByIdAndTechnicalInfoIsDeletedAndIsInStock(carId, false, true);

        User user = searchUser.orElseThrow(() -> new NoSuchEntityException("User with id = " + userId + " does not exist"));
        Car car = searchCar.orElseThrow(() -> new NoSuchEntityException("Car with id = " + carId + " does not exist"));

        if (user.getDrivingLicence() == null) {
            throw new NoSuchEntityException("User with id = " + userId + " does not have a licence");
        }else if (user.getDeal() != null){
            throw new NoSuchEntityException("User with id = " + userId + " also has a deal");
        }
        Map<String, Object> entityMap = new HashMap<>(2);
        entityMap.put("user", user);
        entityMap.put("car", car);
        return entityMap;
    }

    @Transactional
    public Deal createDeal(User user, Car car, Deal deal) {
        deal.setPrice(costCalculation(deal, car));
        car.setIsInStock(false);
        deal.setCar(car);
        deal.setUser(user);
        dealRepository.save(deal);
        mailSender.sendDealDetailsToUser(deal);
        return deal;
    }

    private Double costCalculation(Deal deal, Car car) {
        Double dateDifferenceDay = (deal.getReturnDate().getTime() - deal.getReceivingDate().getTime()) / (8.64 * Math.pow(10, 7));
        return (double) Math.round(dateDifferenceDay * car.getCostPerDay());
    }

    @Transactional
    public Deal updateDeal(User user, Car car, Deal deal) {
        Optional<Deal> searchDeal = dealRepository.findByIdAndTechnicalInfoIsDeleted(deal.getId(), false);
        Deal searchedDeal = searchDeal.orElseThrow(() -> new NoSuchEntityException("User with id = " + deal.getId() + " does not exist"));

        TechnicalInfo technicalInfo = searchedDeal.getTechnicalInfo();
        technicalInfo.setModificationDate(new Timestamp(new Date().getTime()));
        deal.setTechnicalInfo(technicalInfo);

        car.setIsInStock(false);
        deal.setUser(user);
        deal.setCar(car);

        deal.setPrice(costCalculation(deal, car));

        dealRepository.save(deal);
        return deal;
    }

    @Transactional
    public void deleteDeal(Deal deal, User user, Boolean isSuccessfully) {

        archiveService.archive(deal, user, isSuccessfully);

        Car car = deal.getCar();
        car.setIsInStock(true);
        user.setDeal(null);

        userRepository.save(user);
        carRepository.save(car);
    }
}
