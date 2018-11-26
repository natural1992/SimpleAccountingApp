package pl.wojciechbury.simpleAccountingApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.entities.TransferEntity;
import pl.wojciechbury.simpleAccountingApp.models.forms.TransferForm;
import pl.wojciechbury.simpleAccountingApp.models.services.TransferService;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.*;

@Controller
public class TransferController {

    final TransferService transferService;
    final UserSession userSession;

    @Autowired
    public TransferController(TransferService transferService, UserSession userSession){
        this.transferService = transferService;
        this.userSession = userSession;
    }

    @GetMapping("/")
    public String showHome(){
        return "redirect:/user";
    }

    @GetMapping("/transfer/day/{page}")
    public String showTransferScreenDayPage(Model model, @PathVariable("page") int page){
        model.addAttribute("transferList", transferService.loadPageForUserThisDay(page));
        model.addAttribute("isVatPayer",userSession.getUserEntity().isVatPayer());
        model.addAttribute("economicParameters", transferService.loadEconomicParametersForGivenTime(
                LocalDate.now(),
                LocalDate.now().plusDays(1)));
        model.addAttribute("incomeTax", transferService.getIncomeTaxForGivenTime(
                LocalDate.now(),
                LocalDate.now().plusDays(1)));

        return "transferScreenDay";
    }

    @GetMapping("/transfer/month/{page}")
    public String showTransferScreenMonthPage(Model model, @PathVariable("page") int page){
        model.addAttribute("transferList", transferService.loadPageForUserThisMonth(page));
        model.addAttribute("isVatPayer",userSession.getUserEntity().isVatPayer());
        model.addAttribute("economicParameters", transferService.loadEconomicParametersForGivenTime(
                LocalDate.now().with(firstDayOfMonth()),
                LocalDate.now().plusDays(1)));
        model.addAttribute("incomeTax", transferService.getIncomeTaxForGivenTime(
                LocalDate.now().with(firstDayOfMonth()),
                LocalDate.now().plusDays(1)));

        return "transferScreenMonth";
    }

    @GetMapping("/transfer/year/{page}")
    public String showTransferScreenYearPage(Model model, @PathVariable("page") int page){
        model.addAttribute("transferList", transferService.loadPageForUserThisYear(page));
        model.addAttribute("isVatPayer",userSession.getUserEntity().isVatPayer());
        model.addAttribute("economicParameters", transferService.loadEconomicParametersForGivenTime(
                LocalDate.now().with(firstDayOfYear()),
                LocalDate.now().plusDays(1)));
        model.addAttribute("incomeTax", transferService.getIncomeTaxUpTo(LocalDate.now().plusDays(1)));

        return "transferScreenYear";
    }

    @GetMapping("/transfer/add")
    public String showTransferAddingScreen(Model model){
        model.addAttribute("transferForm", new TransferForm());

        return "transferAddScreen";
    }

    @PostMapping("/transfer/add")
    public String getTransferAddingScreen(Model model,
                                    @ModelAttribute @Valid TransferForm transferForm,
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("addTransferInfo", "Your input is incorrect");

            return "transferAddScreen";
        }
        transferService.addTransfer(transferForm);

        return "redirect:/transfer/all";
    }
}
