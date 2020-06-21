package com.StagePFE.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			model.addAttribute("status", status.toString());
		} else {
			model.addAttribute("status", "404");
		}
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
