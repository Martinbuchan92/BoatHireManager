public enum EngineType {Inboard_D4225(225, "Volvo Penta", "D4225", "Diesel"), 
						Inboard_M13(12.2, "Yanmar", "2YM13", "Diesel"), 
						Inboard_M15(16, "Yanmar", "3YM15", "Diesel"), 
						Inboard_M2(16, "Vetus", "M2.18", "Diesel"), 
						Inboard_M20(21, "Yanmar", "3YM20", "Diesel"), 
						Inboard_M4(42, "Vetus", "M4.45", "Diesel"), 
						Inboard_VF4(170, "Vetus", "VF4", "Diesel"), 
						Outboard_F35(35, "Yamaha", "F35", "Petrol"), 
						Outboard_VF4(170, "Vetus", "VF4", "Petrol"), 
						SternDrive_GX225(225, "Volvo Penta", "4.3GXie", "Petrol"),
						SternDrive_VP175(175, "Volvo Penta", "VP175", "Diesel"),
						SternDrive_VP250(250, "Volvo Penta", "VP250", "Diesel");
	
	private String brand; 			// Yamaha, ...
	private double capacity; 		// bhp
	private String fuelType; 		// diesel, petrol
	private String model; 			// VP175, ...
	
	EngineType(double capacity, String brand, String model, String fuelType){
		this.capacity = capacity;
		this.brand = brand;
		this.model = model;
		this.fuelType = fuelType;
	}
	
	public String getBrand(){ return this.brand; }
	public double getCapacity(){ return this.capacity; }
	public String getFuelType(){ return this.fuelType; }
	public String getModel(){ return this.model; }
};