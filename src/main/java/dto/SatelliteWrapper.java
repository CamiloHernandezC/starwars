package dto;

import java.util.List;

import com.meli.starwars.entity.Satellite;

public class SatelliteWrapper {
	
	private List<Satellite> satellites;

	public List<Satellite> getSatellites() {
		return satellites;
	}

	public void setSatellites(List<Satellite> satellites) {
		this.satellites = satellites;
	}
	
}
