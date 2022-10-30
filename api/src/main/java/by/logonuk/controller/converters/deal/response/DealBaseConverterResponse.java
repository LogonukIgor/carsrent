package by.logonuk.controller.converters.deal.response;

import by.logonuk.controller.responses.deal.DealResponse;
import by.logonuk.domain.Deal;
import org.springframework.core.convert.converter.Converter;

public abstract class DealBaseConverterResponse<S, T> implements Converter<S, T> {

    public DealResponse doConvert(DealResponse dealResponse, Deal source) {
        dealResponse.setReturnDate(source.getReturnDate());
        dealResponse.setReceivingDate(source.getReceivingDate());
        dealResponse.setPrice(source.getPrice());
        dealResponse.setId(source.getId());
        return dealResponse;
    }
}
