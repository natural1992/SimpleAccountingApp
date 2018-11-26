package pl.wojciechbury.simpleAccountingApp.models.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.entities.TransferEntity;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TransferForm {

    private TransferEntity.TransferType transferType;
    @Pattern(regexp = "[0-9.]{1,102}")
    private String amount;
    @Pattern(regexp = "[0-9.]{1,5}")
    private String vatRate;
    private String title;
    private String category;
}
