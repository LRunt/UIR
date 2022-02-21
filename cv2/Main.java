package cv2;

public class Main {
	
	public static void hanoj(int N, char start, char finish, char helper) {
		if(N == 1) {
			System.out.print("" + start + ">" + finish + ", ");
		}
		else {
			hanoj(N - 1, start, helper, finish);
			System.out.print("" + start + ">" + finish + ", ");
			hanoj(N - 1, helper, finish, start);
		}
	}
	
	public static int faktorial(int N) {
		if(N == 0) return 1;
		int faktorial = 1;
		for(int i = N; i > 0; i--) {
			faktorial *= i;
		}
		return faktorial;
	}
	
	public static int faktorialR(int N) {
		if(N == 0) return 1;
		if(N == 1) return 1;
		else return N * faktorialR(N - 1);
	}
	
	public static int fibonacciR(int N) {
		if(N == 0) return 0;
		if(N == 1) return 1;
		else return fibonacciR(N - 1) + fibonacciR(N - 2);
	}
	
	public static int fibonacci(int N) {
		if(N == 0) return 0;
		if(N == 1) return 1;
		int[] pole = new int[2];
		pole[0] = 0;
		pole[1] = 1;
		int fibonacci = 0;
		for(int i = 0; i < N - 1; i++) {
			fibonacci = pole[0] + pole[1];
			pole[0] = pole[1];
			pole[1] = fibonacci;
		}
		return fibonacci;
	}

	public static void main(String[] args) {
		hanoj(3, 'A', 'C', 'B');
		System.out.println();
		System.out.println(faktorial(5));
		System.out.println(faktorial(10));
		System.out.println(faktorialR(5));
		System.out.println(faktorialR(10));
		System.out.println(fibonacciR(5));
		System.out.println(fibonacciR(10));
		System.out.println(fibonacciR(20));
		System.out.println(fibonacci(5));
		System.out.println(fibonacci(10));
		System.out.println(fibonacci(20));
	}

}
