package encriptar;

public class encriptacion{
	public String encriptar(String a){
		char mensaje[]=a.toCharArray();
		for(int i=0;i<mensaje.length;i++){
			mensaje[i]=(char)(mensaje[i]+(char)7);
		}
		return (String.valueOf(mensaje));
	}
	public String decepcriptar(String a){
		char mensaje[]=a.toCharArray();
		for(int i=0;i<mensaje.length;i++){
			mensaje[i]=(char)(mensaje[i]-(char)7);
		}
		return (String.valueOf(mensaje));
	}
	public static void main(String[] args){
		new encriptacion();
	}
}