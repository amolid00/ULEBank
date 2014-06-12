package es.unileon.ulebank.brokerage;

import java.util.ArrayList;
import java.util.Scanner;

public class InvestorSuitabilityRequirements {
	
	ArrayList<String> listaProductos = new ArrayList<String>();
	
	private static final String questionOne = "¿Tiene actualmente, o ha tenido en los últimos meses fondos de inversión, acciones y/o obligaciones? Si es así, marcar los productos que tiene o ha tenido.";
	private static final String questionTwo = "Indique su nivel de estudios.";
	private static final String questionThree = "¿Tiene relación entre su profesión y el ámbito financiero?";
	private static final String questionFour = "Fuentes de información que utiliza de forma habitual.";
	private static final String questionFive = "buscan obtener una rentabilidad positiva, aunque ésta sea inferior a la inflación.";
	private static final String questionSix = "buscan obtener una rentabilidad positiva, aunque ésta sea igual a la inflación.";
	private static final String questionSeven = "buscan obtener una rentabilidad positiva, aunque ésta sea superior a la inflación.";
	
	private Scanner scanner;
	
	private boolean responseOne;
	private int responseTwo;
	private boolean responseThree;
	private boolean responseFour;
	private boolean responseFive;
	private boolean responseSix;
	private boolean responseSeven;
	
	public InvestorSuitabilityRequirements() {
		this.scanner = new Scanner(System.in);
		this.responseOne = false;
		this.responseTwo = -1;
		this.responseThree =false;
		this.responseFour = false;
		this.responseFive = false;
		this.responseSix = false;
		this.responseSeven = false;
	}
	
	public void investorTest() {
		
		System.out.println(questionOne);
//		responseOne = this.scanner.nextBoolean();
//		System.out.println(responseOne);
		String response= this.scanner.next();
		if(response.toLowerCase().equals("si")){
			this.responseOne = true;
		}else if(response.toLowerCase().equals("no")){
			this.responseOne = false;
		}
		
		System.out.println(this.responseOne);
		
		StringBuffer sb = new StringBuffer();
		sb.append("Indique su nivel de estudios en función a la siguiente tabla:\n");
		sb.append("Sin Estudios: 1\n");
		sb.append("Estudios primarios: 2\n");
		sb.append("Titulo ESO o similar: 3\n");
		sb.append("Titulo COU o Bachilerato LOGSE: 4\n");
		sb.append("Titulo universitario: 5\n");
		
		System.out.println(sb);
		this.responseTwo = this.scanner.nextInt();
		
//		if (this.responseTwo == 1) {
//			this.responseTwo ==;
//		}else if(){
//			
//		}
	}
	
	public static void main(String[] args) {
		new InvestorSuitabilityRequirements().investorTest();;
	}
	
//	public void InvestorTest() {
//
//		Scanner in = new Scanner(System.in);
//		int num1, num2 = 1, num3 = 1, num4 = 1, num5 = 1, num6, num7;
//		Scanner scanner = new Scanner(System.in);
//
//		System.out
//				.println("¿Tiene actualmente, o ha tenido en los últimos meses fondos de inversión, acciones y/o obligaciones? Si es así, marcar los productos que tiene o ha tenido.");
//		String name = scanner.nextLine();
//		System.out.println("Nivel de estudios.");
//		String acom = scanner.nextLine();
//		System.out
//				.println("¿Tiene relación entre su profesión y el ámbito financiero? [1] SI [2] NO");
//		num1 = in.nextInt();
//		if (num1 == 1) {
//			System.out
//					.println("Fuentes de información que utiliza de forma habitual. ");
//			String name01 = scanner.nextLine();
//			System.out.println(name + " quiere ir al cine con " + acom
//					+ " y quiere ver la pelicula " + name01);
//		} else {
//			System.out.println("¿Quieres ir a un cafe?[1] SI [2] NO");
//			num2 = in.nextInt();
//			if (num2 == 1) {
//				System.out.println("¿Que bebida deseas tomar?");
//				String name02 = scanner.nextLine();
//				System.out.println(name + " quiere ir al cafe con " + acom
//						+ " y quiere tomar " + name02);
//			} else {
//				System.out.println("¿Quieres ir al parque? [1] SI [2] NO");
//				num3 = in.nextInt();
//				if (num3 == 1) {
//					System.out.println("¿que quieres hacer en el parque?");
//					String name03 = scanner.nextLine();
//					System.out.println(name + " quiere ir al parque con "
//							+ acom + " a pasear y quiere " + name03);
//
//				} else {
//					System.out.println("¿Quieres ir a tu casa? [1] SI [2] NO");
//					num4 = in.nextInt();
//					if (num4 == 1) {
//						System.out.println("¿Que quieres hacer en tu casa?");
//						String name04 = scanner.nextLine();
//						System.out.println(name + " quiere ir a su casa con "
//								+ acom + " y quiere " + name04);
//					} else {
//						System.out
//								.println("¿No quieres hacer algo o si quieres?[1] si quiero [2] no quiero ");
//						num5 = in.nextInt();
//						if (num5 == 1) {
//							System.out.println("¿A donde quieres ir?");
//							String name06 = scanner.nextLine();
//							System.out.println("¿que quieres hacer en "
//									+ name06 + "?");
//							String name05 = scanner.nextLine();
//							System.out.println(name + " quiere ir a " + name06
//									+ " y quiere " + name05 + " con " + acom);
//						} else {
//							System.out.println(name + " es un forever alone");
//						}
//					}
//				}
//				{
//
//				}
//			}
//		}
//	}
}
