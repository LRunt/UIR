def factorial(x):
    faktorial = 1
    for x in range(x, 0, -1):
        faktorial *= x
    return faktorial

def fibonacci(x):
    if x == 0: return 0
    if x == 1: return 1
    else: return fibonacci(x - 1) + fibonacci(x - 2)

x = 20
factorialVypis = "{}! = {}"
fibonacciVypis = "Fibonacci {} = {}"
print(factorialVypis.format(x, factorial(x)))
print(fibonacciVypis.format(x, fibonacci(x)))