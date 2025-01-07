# **Banking Management System**

This project is a Java-based banking application that provides features for deposits, withdrawals, and transfers, while managing accounts and operations efficiently. The project implements custom exceptions to handle specific error scenarios, ensuring robust error management. [Versión Español](./README.es.md)

---

## **Features**

### Account Operations:
- Deposit funds with currency conversion.
- Withdraw funds with balance validation.
- Transfer funds between accounts with restrictions.

### Custom Exception Handling:

- `SaldoMaximoSuperadoException`: Prevents exceeding the account's maximum balance.
- `FondosInsuficientesException`: Ensures withdrawals and transfers are within the available balance.
- `CuentasIgualesException`: Avoids transfers to the same account.

### Account and Operation Management:
- Manage user accounts with secure balance updates.
- List and query deposits and transfers.
- Update transfer details dynamically.

### Menu-Driven User Interface:
- Intuitive text-based interface for user interaction.

## **Project Structure**

```plaintext
Banking Management System/
├── src/main/java/         
    ├── bankingManagementSystem 
        ├── Menu.java            
        ├── Banco.java        
        ├── Cuenta.java       
        ├── Operacion.java    
        ├── Deposito.java       
        ├── Transferencia.java 
    ├── exceptions              
        ├── CuentasIgualesException.java        
        ├── FondosInsuficientesException.java       
        ├── SaldoMaximoSuperadoException.java    
```

## **Main Components**

| Class                   | Description                                                                   |
|-------------------------|-------------------------------------------------------------------------------|
| `Banco`                 | Central class for managing accounts, deposits, and transfers.functionalities. |
| `Cuenta`                | Represents individual bank accounts with account number and balance.          |
| `Operacion (Interface)` | Defines the structure for banking operations (e.g., deposits and transfers).  |
| `Deposito`              | Implementation of the Operacion interface for deposit transactions.           |
| `Transferencia`         | Implementation of the Operacion interface for fund transfers.                 |
| `Menu`                  | Provides the user interface to interact with the banking system.              |
---

## **Usage**

### 1. Clone the repository:
```bash
git clone https://github.com/mcortezv/Banking-Management-System
```

### 2. Navigate to the project directory:
```bash
cd Banking-Management-System
```

### 3. Compile the project:
```bash
javac -Xlint:-options -source 8 -target 8 -d out src/main/java/bankingManagementSystem/*.java src/main/java/exceptions/*.java
```

### 4. Run the application:
```bash
java -cp out bankingManagementSystem.Menu
```

## **License**
This project is licensed under the MIT License. See the [LICENSE](./LICENSE.md) file for more details.