package com.packa.japp.naivechain;

import com.google.gson.Gson;
import com.packa.japp.domain.HistoriaClinica;

public class HistoriaMapper {

	private static String SEPARATOR = "-";

	public class HistoriaDTO {
		public HistoriaDTO(HistoriaClinica hist) {
			medico = hist.getMedico().getId() + SEPARATOR + hist.getMedico().getNombre() + SEPARATOR
					+ hist.getMedico().getEspecialidad();
			paciente = hist.getPaciente().getDni() + SEPARATOR + hist.getPaciente().getNombre();
			institucion = hist.getInstitucion().getId() + SEPARATOR + hist.getInstitucion().getNombre();
			sintoma = hist.getSintoma().getId() + SEPARATOR + hist.getSintoma().getDescripcion();
		}

		String medico;
		String paciente;
		String institucion;
		String sintoma;

	}

	public String convertirHistoria(HistoriaClinica historiaClinica) {
		HistoriaDTO historia = new HistoriaDTO(historiaClinica);
		Gson gson = new Gson();
		String str = gson.toJson(historia);
		return str;

	}
}
