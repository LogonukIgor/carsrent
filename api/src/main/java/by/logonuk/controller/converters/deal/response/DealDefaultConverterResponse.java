package by.logonuk.controller.converters.deal.response;

import by.logonuk.controller.responses.DealResponse;
import by.logonuk.domain.Deal;
import org.springframework.stereotype.Component;

@Component
public class DealDefaultConverterResponse extends DealBaseConverterResponse<Deal, DealResponse> {

    @Override
    public DealResponse convert(Deal source) {
        DealResponse dealResponse = new DealResponse();
        dealResponse.setDealCar(source.getCar());
        return doConvert(dealResponse, source);
    }
}
