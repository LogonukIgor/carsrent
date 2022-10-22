package by.logonuk.controller.converters.deal.request;

import by.logonuk.controller.requests.DealCreateRequest;
import by.logonuk.domain.Deal;
import org.springframework.core.convert.converter.Converter;

public abstract class DealBaseConverterRequest<S, T> implements Converter<S, T> {

    public Deal doConvert(Deal deal, DealCreateRequest source) {
        deal.setReceivingDate(source.getReceivingDate());
        deal.setReturnDate(source.getReturnDate());
        return deal;
    }
}
