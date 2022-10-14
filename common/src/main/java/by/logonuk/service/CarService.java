package by.logonuk.service;

import by.logonuk.domain.Car;
import by.logonuk.domain.CarManufactury;
import by.logonuk.domain.Classification;
import by.logonuk.domain.Model;
import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.repository.CarManufacturyRepository;
import by.logonuk.repository.CarRepository;
import by.logonuk.repository.ClassificationRepository;
import by.logonuk.repository.LibraryRepository;
import by.logonuk.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final ModelRepository modelRepository;

    private final CarManufacturyRepository carManufacturyRepository;

    private final ClassificationRepository classificationRepository;

    private final LibraryRepository libraryRepository;

    @Transactional
    public void createCar(Car car, Model model, Classification classification, CarManufactury carManufactury, TechnicalDatesAndInfo technicalDatesAndInfo){
        Model searchModel = modelValid(model);

        CarManufactury searchCarManufactury = carManufacturyValid(carManufactury);

        Classification searchClassification = classificationRepository.findByClassificationLetter(classification.getClassificationLetter());

        carRepository.save(car);

        libraryRepository.customSave(searchCarManufactury.getId(), searchModel.getId(), searchClassification.getId(), car.getId());
    }

    @Transactional
    public Model modelValid(Model model){
        Optional<Model> searchModel = modelRepository.findByModelName(model.getModelName());
        if(searchModel.isPresent()){
            Model mainModel = searchModel.get();
            return mainModel;
        }
        modelRepository.save(model);
        return model;
    }

    @Transactional
    public CarManufactury carManufacturyValid(CarManufactury carManufactury){
        Optional<CarManufactury> searchCarManufactury = carManufacturyRepository.findByCountryOfOrigin(carManufactury.getCountryOfOrigin());
        if(searchCarManufactury.isPresent()){
            CarManufactury mainCarManufactury = searchCarManufactury.get();
            return mainCarManufactury;
        }
        carManufacturyRepository.save(carManufactury);
        return carManufactury;
    }
}
