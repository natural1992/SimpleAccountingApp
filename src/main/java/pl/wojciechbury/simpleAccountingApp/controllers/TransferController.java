package pl.wojciechbury.simpleAccountingApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransferController {

    @GetMapping("/transfer")
    public String showTransferScreen(Model model){

        return "";
    }
}
