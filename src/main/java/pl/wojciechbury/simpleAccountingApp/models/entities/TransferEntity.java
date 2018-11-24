package pl.wojciechbury.simpleAccountingApp.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.forms.TransferForm;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transfer")
@NoArgsConstructor
public class TransferEntity {
    public enum transferType{
        COST,
        INCOME
    }
    @GeneratedValue
    @Id
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "transfer_type")
    private transferType transferType;
    private double amount;
    @Column(name = "vat_rate")
    private double vatRate;
    @GeneratedValue
    private LocalDateTime date;
    private String category;

    public TransferEntity(TransferForm transferForm, UserSession userSession){
        userId = userSession.getUserEntity().getId();
        transferType = transferForm.getTransferType();
        amount = transferForm.getAmount();
        vatRate = transferForm.getVatRate();
        category = transferForm.getCategory();
    }

}

