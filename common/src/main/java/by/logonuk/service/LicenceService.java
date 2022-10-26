package by.logonuk.service;

import by.logonuk.domain.DrivingLicence;
import by.logonuk.domain.User;
import by.logonuk.domain.attachments.TechnicalInfo;
import by.logonuk.exception.CreateLicenceForUserException;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.DrivingLicenceRepository;
import by.logonuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenceService {

    private final DrivingLicenceRepository repository;

    private final UserRepository userRepository;

    @Transactional
    public User validateLicenceForUser(String userIdForValidation){
        Long userId = Long.parseLong(userIdForValidation);
        Optional<User> searchUser = userRepository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        User user = searchUser.orElseThrow(()->new NoSuchEntityException("User with this id = " +  userId + " does not exist"));
        Optional<DrivingLicence> searchLicenceByUser = repository.customFindByUserId(userId);
        if(searchLicenceByUser.isPresent()){
            throw new CreateLicenceForUserException("The user with id = " + userId + " already has a license");
        }else {
            return user;
        }
    }

    @Transactional
    public User validateLicenceForUserUpdate(String userIdForValidation){
        Long userId = Long.parseLong(userIdForValidation);
        Optional<User> searchUser = userRepository.findByIdAndTechnicalInfoIsDeleted(userId, false);
        User user = searchUser.orElseThrow(()->new NoSuchEntityException("User with this id = " +  userId + " does not exist"));
        Optional<DrivingLicence> searchLicenceByUser = repository.customFindByUserId(userId);
        return user;
    }

    @Transactional
    public DrivingLicence createLicence(User user, DrivingLicence drivingLicence){
        drivingLicence.setUser(user);
        repository.save(drivingLicence);
        return drivingLicence;
    }

    @Transactional
    public DrivingLicence updateLicence(User user, DrivingLicence drivingLicence) {
        drivingLicence.setUser(user);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        TechnicalInfo technicalInfo = new TechnicalInfo(user.getDrivingLicence().getTechnicalInfo().getCreationDate(), timestamp, false);
        drivingLicence.setTechnicalInfo(technicalInfo);
        repository.save(drivingLicence);
        return drivingLicence;
    }
}
