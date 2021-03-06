package com.SharedMusic.Shared.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.lang3.StringUtils;

import com.SharedMusic.Shared.entity.Cancion;
import com.SharedMusic.Shared.service.CancionService;

@Controller
@RequestMapping("/cancion")
public class CancionController {
	@Autowired
	CancionService cancionService;

	@GetMapping("lista")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/cancion/lista");
		List<Cancion> cancion = cancionService.list();
		mv.addObject("cancion", cancion);
		return mv;
	}

	@GetMapping("nuevo")
	public String nuevo() {
		return "cancion/nuevo";
	}

	@PostMapping("/guardar")
	public ModelAndView crear(@RequestParam String genero, @RequestParam String nombre, @RequestParam String link,
			@RequestParam String artista) {

		ModelAndView mv = new ModelAndView();
		if (StringUtils.isBlank(nombre)) {
			mv.setViewName("cancion/nuevo");
			mv.addObject("error", "el nombre no puede estar vacío");
			return mv;
		}

		Cancion cancion = new Cancion(genero, nombre, link, artista, "0", "0");
		cancionService.save(cancion);
		mv.setViewName("redirect:/cancion/lista");
		return mv;
	}

	@GetMapping("/detalle/{id}")
	public ModelAndView detalle(@PathVariable("id") int id) {
		if (!cancionService.existsById(id))
			return new ModelAndView("redirect:/cancion/lista");
		Cancion cancion = cancionService.getOne(id).get();
		ModelAndView mv = new ModelAndView("/cancion/detalle");
		mv.addObject("cancion", cancion);
		return mv;
	}

	@PostMapping(value = "/buscar")
	public ModelAndView buscar(@RequestParam String busquedaId, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		int busId = 0;
		try {
			busId = Integer.parseInt(busquedaId);
		} catch (Exception e) {
		}
		if (!cancionService.existsById(busId)) {
			redirectAttrs.addFlashAttribute("error", "Esa cancion no existe");
			return new ModelAndView("redirect:/cancion/lista");
		}

		Cancion cancion = cancionService.getOne(busId).get();
		ModelAndView mv = new ModelAndView("/cancion/detalle");
		mv.addObject("cancion", cancion);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		if (!cancionService.existsById(id))
			return new ModelAndView("redirect:/cancion/lista");
		Cancion cancion = cancionService.getOne(id).get();
		ModelAndView mv = new ModelAndView("/cancion/editar");
		mv.addObject("cancion", cancion);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/actualizar")
	public ModelAndView actualizar(@RequestParam int id, @RequestParam String genero, @RequestParam String nombre,
			@RequestParam String link, @RequestParam String artista) {
		if (!cancionService.existsById(id))
			return new ModelAndView("redirect:/cliente/lista");
		ModelAndView mv = new ModelAndView();
		Cancion cancion = cancionService.getOne(id).get();
		if (StringUtils.isBlank(nombre)) {
			mv.setViewName("cancion/editar");
			mv.addObject("cancion", cancion);
			mv.addObject("error", "el nombre no puede estar vacío");
			return mv;
		}

		if (cancionService.existsByNombre(nombre) && cancionService.getByNombre(nombre).get().getId() != id) {
			mv.setViewName("cancion/editar");
			mv.addObject("error", "ese nombre ya existe");
			mv.addObject("cancion", cancion);
			return mv;
		}

		cancion.setGenero(genero);
		cancion.setNombre(nombre);
		cancion.setLink(link);
		cancion.setArtista(artista);
		cancionService.save(cancion);
		return new ModelAndView("redirect:/cancion/lista");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrar/{id}")
	public ModelAndView borrar(@PathVariable("id") int id) {
		try {
			if (cancionService.existsById(id)) {
				cancionService.delete(id);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/cancion/lista");
	}

	@GetMapping("/like/{id}")
	public ModelAndView likeCancion(@PathVariable("id") int id) {
		Cancion cancion = cancionService.getOne(id).get();
		try {
			if (cancionService.existsById(id)) {
				int likes = Integer.parseInt(cancion.getCantidad_likes());
				likes++;
				cancion.setCantidad_likes(Integer.toString(likes));
				cancionService.save(cancion);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/index");
	}

	@PostMapping(value = "/ordenar")
	public ModelAndView ordenamiento(@RequestParam String ordenarPor, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index");
		List<Cancion> cancion = cancionService.list();
		System.out.println(cancion.size());
		mv.addObject("cancion", cancion);
		if (ordenarPor.equalsIgnoreCase("Numero de me gusta")) {
			Collections.sort(cancion, new Comparator<Cancion>() {
				@Override
				public int compare(Cancion o1, Cancion o2) {
					return o2.getCantidad_likes().compareToIgnoreCase(o1.getCantidad_likes());
				}
			});
		}
		if (ordenarPor.equalsIgnoreCase("Nombre de la cancion (A-Z)")) {
			Collections.sort(cancion, new Comparator<Cancion>() {
				@Override
				public int compare(Cancion o1, Cancion o2) {
					return o1.getNombre().compareToIgnoreCase(o2.getNombre());
				}
			});
		}
		if (ordenarPor.equalsIgnoreCase("Genero (A-Z)")) {
			Collections.sort(cancion, new Comparator<Cancion>() {
				@Override
				public int compare(Cancion o1, Cancion o2) {
					return o1.getGenero().compareToIgnoreCase(o2.getGenero());
				}
			});
		}
		if (ordenarPor.equalsIgnoreCase("Artista (A-Z)")) {
			Collections.sort(cancion, new Comparator<Cancion>() {
				@Override
				public int compare(Cancion o1, Cancion o2) {
					return o1.getArtista().compareToIgnoreCase(o2.getArtista());
				}
			});
		}

		return mv;
	}

}