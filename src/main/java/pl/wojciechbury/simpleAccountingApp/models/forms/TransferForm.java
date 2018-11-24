package pl.wojciechbury.simpleAccountingApp.models.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.entities.TransferEntity;

@Data
@NoArgsConstructor
public class TransferForm {

    private TransferEntity.transferType transferType;
    private double amount;
    private double vatRate;
    private String title;
    private String category;
}
