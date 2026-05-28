import sys

def add(a, b):
    return a + b

def subtract(a, b):
    return a - b

def multiply(a, b):
    return a * b

def divide(a, b):
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b

def modulo(a, b):
    if b == 0:
        raise ValueError("Cannot take modulo by zero")
    return a % b

# Map operators to functions
operations = {
    '+': add,
    '-': subtract,
    '*': multiply,
    '/': divide,
    '%': modulo
}

def main():
    print("Simple CLI Calculator")
    print("Available operators: +, -, *, /, %")
    print("Type 'quit' to exit.\n")

    while True:
        op = input("Enter operator (+, -, *, /, %) or 'quit': ").strip()
        if op.lower() == 'quit':
            print("Goodbye!")
            sys.exit(0)

        if op not in operations:
            print("Invalid operator. Please use +, -, *, /, %\n")
            continue

        try:
            num1 = float(input("Enter first number: "))
            num2 = float(input("Enter second number: "))
        except ValueError:
            print("Invalid number. Please enter numeric values.\n")
            continue

        func = operations[op]
        try:
            result = func(num1, num2)
            # Print result as integer if it's a whole number
            if result.is_integer():
                result = int(result)
            print(f"Result: {num1} {op} {num2} = {result}\n")
        except ValueError as e:
            print(f"Error: {e}\n")

if __name__ == "__main__":
    main()
