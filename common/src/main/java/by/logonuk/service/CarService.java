package by.logonuk.service;

import by.logonuk.domain.Car;
import by.logonuk.domain.CarManufacture;
import by.logonuk.domain.Classification;
import by.logonuk.domain.Library;
import by.logonuk.domain.Model;
import by.logonuk.repository.CarManufactureRepository;
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

    private final CarManufactureRepository carManufactureRepository;

    private final ClassificationRepository classificationRepository;

    private final LibraryRepository libraryRepository;

    @Transactional
    public Car createCar(Car car, Model model, Classification classification, CarManufacture carManufacture){
        Model searchModel = modelValid(model);
        CarManufacture searchCarManufacture = carManufactureValid(carManufacture);
        Classification searchClassification = classificationRepository.findByClassificationLetter(classification.getClassificationLetter());

        carRepository.save(car);

        libraryRepository.customSave(searchCarManufacture.getId(), searchModel.getId(), searchClassification.getId(), car.getId());
        return carForResponse(car);
    }

    @Transactional
    public Car updateCar(Car car, Model model, Classification classification, CarManufacture carManufacture){
        Model searchModel = modelValid(model);
        CarManufacture searchCarManufacture = carManufactureValid(carManufacture);
        Classification searchClassification = classificationRepository.findByClassificationLetter(classification.getClassificationLetter());
        carRepository.save(car);

        Library library = libraryRepository.findByCarId(car.getId());
        library.setModel(searchModel);
        library.setCarManufacture(searchCarManufacture);
        library.setClassification(searchClassification);
        return carForResponse(car, library);
    }

    @Transactional
    public Model modelValid(Model model){
        Optional<Model> searchModel = modelRepository.findByModelName(model.getModelName());
        if(searchModel.isPresent()){
            return searchModel.get();
        }
        modelRepository.save(model);
        return model;
    }

    @Transactional
    public CarManufacture carManufactureValid(CarManufacture carManufacture){
        Optional<CarManufacture> searchCarManufacture = carManufactureRepository.findByCountryOfOrigin(carManufacture.getCountryOfOrigin());
        if(searchCarManufacture.isPresent()){
            return searchCarManufacture.get();
        }
        carManufactureRepository.save(carManufacture);
        return carManufacture;
    }

    private Car carForResponse(Car savedCar){
        Library library = libraryRepository.findByCarId(savedCar.getId());
        savedCar.setCarInfo(library);
        return savedCar;
    }
    private Car carForResponse(Car savedCar, Library library){
        savedCar.setCarInfo(library);
        return savedCar;
    }
}
