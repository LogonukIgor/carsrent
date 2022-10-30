package by.logonuk.service;

import by.logonuk.domain.Archive;
import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import by.logonuk.domain.attachments.TechnicalInfo;
import by.logonuk.repository.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository repository;

    public void archive(Deal deal, User user, Boolean isSuccessfully) {
        Archive archive = new Archive();

        archive.setIsSuccessfully(isSuccessfully);
        archive.setUserId(user.getId());
        archive.setCarId(deal.getCar().getId());

        archive.setReceivingDate(deal.getReceivingDate());
        archive.setReturnDate(deal.getReturnDate());

        archive.setPrice(deal.getPrice());

        TechnicalInfo technicalInfo = deal.getTechnicalInfo();
        technicalInfo.setIsDeleted(true);
        archive.setTechnicalInfo(technicalInfo);
        repository.save(archive);
    }
}
