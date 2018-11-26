package pl.wojciechbury.simpleAccountingApp.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.entities.TransferEntity;
import pl.wojciechbury.simpleAccountingApp.models.forms.TransferForm;
import pl.wojciechbury.simpleAccountingApp.models.repositories.TransferRepository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.*;

@Service
public class TransferService {

    final TransferRepository transferRepository;
    final UserSession userSession;

    @Autowired
    public TransferService(TransferRepository transferRepository,
                           UserSession userSession){
        this.transferRepository = transferRepository;
        this.userSession = userSession;
    }

    public Page<TransferEntity> loadPageForUser(int page){
        return transferRepository.findAllByUserId(userSession.getUserEntity().getId(), PageRequest.of(page, 15));
    }

    public void addTransfer(TransferForm transferForm) {
        TransferEntity newTransfer = new TransferEntity(transferForm, userSession.getUserEntity());
        transferRepository.save(newTransfer);
    }

    public Page<TransferEntity> loadPageForUserThisDay(int page){
        return transferRepository.findByUserIdBetweenDates(
                userSession.getUserEntity().getId(),
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                PageRequest.of(page, 15));
    }

    public Page<TransferEntity> loadPageForUserThisMonth(int page){
        return transferRepository.findByUserIdBetweenDates(
                userSession.getUserEntity().getId(),
                LocalDate.now().with(firstDayOfMonth()),
                LocalDate.now().with(firstDayOfNextMonth()),
                PageRequest.of(page, 15));
    }

    public Page<TransferEntity> loadPageForUserThisYear(int page){
        return transferRepository.findByUserIdBetweenDates(
                userSession.getUserEntity().getId(),
                LocalDate.now().with(firstDayOfYear()),
                LocalDate.now().with(firstDayOfNextYear()),
                PageRequest.of(page, 15));
    }

    public Map<String, Double> loadEconomicParametersForGivenTime(LocalDate startDate, LocalDate endDate){ //TODO make it shorter
        List<TransferEntity> transfers = transferRepository.findTransfersForUserForGivenTime(
                userSession.getUserEntity().getId(),
                startDate,
                endDate);

        double maxCost = 0;
        double maxIncome = 0;
        double vatReturn = 0;

        for(int i = 0; i < transfers.size(); i++){
            if(transfers.get(i).getTransferType().equals(TransferEntity.TransferType.COST)){
                maxCost = Math.max(transfers.get(i).getAmount(), maxCost);
                vatReturn += transfers.get(i).getAmount() * transfers.get(i).getVatRate();
            }else{
                maxIncome = Math.max(transfers.get(i).getAmount(), maxIncome);
                vatReturn -= transfers.get(i).getAmount() * transfers.get(i).getVatRate();
            }
        }
        Map<String, Double> result = new HashMap<>();
        {
            result.put("biggestCost", (double) Math.round(maxCost * 100) / 100 );
            result.put("biggestIncome", (double) Math.round(maxIncome * 100) / 100);
            result.put("vatReturn", (double) Math.round(vatReturn * 100) / 100);
        }
        return result;
    }

    public double getIncomeTaxUpTo(LocalDate date){
        List<TransferEntity> transfers = transferRepository.findTransfersForUserForGivenTime(
                userSession.getUserEntity().getId(),
                date.with(firstDayOfYear()),
                date);
        double incomeTax = 0;

        for(int i = 0; i < transfers.size(); i++){
            if(transfers.get(i).getTransferType().equals(TransferEntity.TransferType.COST)){
                incomeTax -= transfers.get(i).getAmount();
            }else{
                incomeTax += transfers.get(i).getAmount();
            }
        }
        if(userSession.getUserEntity().isLinearIncomeTaxPayer()){
            incomeTax = incomeTax * 0.19;
        }else {
            incomeTax = (Math.min(incomeTax, 85528) - 556.02) * 0.18 + Math.max(0, incomeTax - 85528) * 0.32;
        }
        return (double) Math.round(incomeTax * 100) / 100;
    }

    public double getIncomeTaxForGivenTime(LocalDate startDate, LocalDate endDate){
        return getIncomeTaxUpTo(endDate) - getIncomeTaxUpTo(startDate);
    }
}
