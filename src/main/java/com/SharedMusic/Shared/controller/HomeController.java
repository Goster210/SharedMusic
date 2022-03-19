package com.SharedMusic.Shared.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.SharedMusic.Shared.entity.Cancion;
import com.SharedMusic.Shared.service.CancionService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	CancionService cancionService;

	@GetMapping(value = { "/", "/index" })
	public ModelAndView rellenarFormularioIndex() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index");
		List<Cancion> cancion = cancionService.list();
		mv.addObject("cancion", cancion);
		return mv;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/forbidden")
	public String forbidden() {
		return "forbidden";
	}

}
