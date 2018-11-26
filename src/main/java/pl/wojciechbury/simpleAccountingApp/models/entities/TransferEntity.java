package pl.wojciechbury.simpleAccountingApp.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.forms.TransferForm;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transfer")
@NoArgsConstructor
public class TransferEntity {
    public enum TransferType {
        COST,
        INCOME
    }
    @GeneratedValue
    @Id
    private int id;
    @Column(name = "userId")
    private int userId;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransferType transferType;
    private double amount;
    @Column(name = "vat_rate")
    private double vatRate;
    private LocalDateTime date;
    private String title;
    private String category;

    public TransferEntity(TransferForm transferForm, UserEntity userEntity){
        userId = userEntity.getId();
        transferType = transferForm.getTransferType();
        amount = Double.valueOf(transferForm.getAmount());
        vatRate = Double.valueOf(transferForm.getVatRate());
        category = transferForm.getCategory();
        title = transferForm.getTitle();
        date = LocalDateTime.now();
    }

}

