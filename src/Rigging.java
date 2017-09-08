public enum Rigging{ GUNTER("single fore-and-aft rigged mast in two pieces", "Gunter"),
					 KETCH("two fore-and-aft rigged masts, mizzen mast before the tiller", "Ketch"),
					 SLOOP("single fore-and-aft rigged mast and a jib", "Sloop");
	
	private String name;
	private String sails;
	
	Rigging(String sails, String name){
		this.sails = sails;
		this.name = name;
	}
	
	public String getName(){ return this.name; }
	public String getSails(){ return this.sails; }
};