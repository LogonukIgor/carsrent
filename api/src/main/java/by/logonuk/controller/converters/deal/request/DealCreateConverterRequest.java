package by.logonuk.controller.converters.deal.request;

import by.logonuk.controller.requests.DealCreateRequest;
import by.logonuk.domain.Deal;
import by.logonuk.domain.attachments.TechnicalInfo;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class DealCreateConverterRequest extends DealBaseConverterRequest<DealCreateRequest, Deal>{
    @Override
    public Deal convert(DealCreateRequest source) {
        Deal deal = new Deal();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        TechnicalInfo technicalInfo = new TechnicalInfo(timestamp, timestamp, false);
        deal.setTechnicalInfo(technicalInfo);
        return doConvert(deal, source);
    }
}
