package es.steria.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.steria.rest.excepciones.DAOLogException;
import es.steria.rest.excepciones.InternalServerErrorException;
import es.steria.rest.excepciones.NotFoundException;
import es.steria.rest.genericos.servicio.ServicioGenerico;
import es.steria.rest.model.Busines;
import es.steria.rest.util.ListBusines;
import es.steria.rest.util.ServicesProvider;

@Controller
@RequestMapping("/rest")
public class BusinesController {

	private static final Log LOG = LogFactory.getLog(BusinesController.class);

	private ServicesProvider provider = ServicesProvider.getInstance();

	@RequestMapping(value = "/busines/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Busines getBusines(@PathVariable("id") String id) {
		LOG.debug("getBusines");
		ServicioGenerico<Busines, String> servicio = (ServicioGenerico<Busines, String>) provider
				.getServicio("servicioBusines");
		try {
			Busines busines = servicio.read(id);
			if (busines == null) {
				throw new NotFoundException();
			} else {
				return busines;
			}
		} catch (DAOLogException e) {
			LOG.error(e);
			throw new InternalServerErrorException();
		}
	}

	@RequestMapping(value = "/busines", method = RequestMethod.POST, headers = "Accept=application/xml,application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Busines createBusines(@RequestBody Busines busines) {
		LOG.debug("createBusines");
		ServicioGenerico<Busines, String> servicio = (ServicioGenerico<Busines, String>) provider
				.getServicio("servicioBusines");
		try {
			servicio.create(busines);
		} catch (DAOLogException e) {
			LOG.error(e);
			throw new InternalServerErrorException();
		}
		return busines;
	}

	@RequestMapping(value = "/busines/{id}", method = RequestMethod.PUT, headers = "Accept=application/xml,application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Busines updateBusines(@PathVariable("id") String id,
			@RequestBody Busines busines) {
		LOG.debug("updateBusines");
		ServicioGenerico<Busines, String> servicio = (ServicioGenerico<Busines, String>) provider
				.getServicio("servicioBusines");
		busines.setId(id);
		try {
			servicio.update(busines);
		} catch (DAOLogException e) {
			LOG.error(e);
			throw new InternalServerErrorException();
		}
		return busines;
	}

	@RequestMapping(value = "/busines/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody
	void deleteBusines(@PathVariable("id") String id) {
		LOG.debug("deleteBusines");
		ServicioGenerico<Busines, String> servicio = (ServicioGenerico<Busines, String>) provider
				.getServicio("servicioBusines");
		try {
			servicio.delete(id);
		} catch (DAOLogException e) {
			LOG.error(e);
			throw new InternalServerErrorException();
		}
	}

	@RequestMapping(value = "/busines", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ListBusines getAll() {
		LOG.debug("getAll");
		ServicioGenerico<Busines, String> servicio = (ServicioGenerico<Busines, String>) provider
				.getServicio("servicioBusines");
		try {
			ListBusines lis = new ListBusines(servicio.getList());
			return lis;
		} catch (DAOLogException e) {
			LOG.error(e);
			throw new InternalServerErrorException();
		}
	}
	
	@RequestMapping(value= "/busines/nombre/{nombre}",
			method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)	
	public @ResponseBody ListBusines findBusines(@PathVariable("nombre") String nombre) {
		LOG.debug("findBusines");
		LOG.debug("Nombre: " + nombre);
		ServicioGenerico<Busines, String> servicio = (ServicioGenerico<Busines, String>) provider
			.getServicio("servicioBusines");
		Busines businesExample = new Busines();
		businesExample.setNombre(nombre);
		ListBusines listado = new ListBusines(servicio.findByExample(businesExample));
		return listado;
		
	}
}
