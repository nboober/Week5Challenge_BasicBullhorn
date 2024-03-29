package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String showMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "home";
    }

    @GetMapping("/add")
    public String newMessage(Model model){
        model.addAttribute("bullhorn", new Bullhorn());
        return "messageForm";
    }

    @PostMapping("/process")
    public String processMessage(@Valid Bullhorn bullhorn, BindingResult result, Model model){
        if(result.hasErrors()){
            return "messageForm";
        }
//        model.addAttribute("bullhorn", bullhorn);
        messageRepository.save(bullhorn);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "messageForm";
    }

    @RequestMapping("/detail/{id}")
    public String viewMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("bullhorn", messageRepository.findById(id).get());
        return "viewMessage";
    }

    @RequestMapping("/delete/{id}")
    public String deleteMessage(@PathVariable("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/";
    }

}
