
public class ComplexNumber2 {

	private double real=0;
	public static ComplexNumber2 of(double givenReal) {
		ComplexNumber2 result = new ComplexNumber2();
		result.real = givenReal;
		
		return result;
	}
	public Double getReal() {
		return real;
	}
	public Double getImagine() {
		// TODO Auto-generated method stub
		return null;
	}

}
