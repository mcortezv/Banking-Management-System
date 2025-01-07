# **Sistema de Gestión Bancaria**

Este proyecto es una aplicación bancaria basada en Java que ofrece funciones para depósitos, retiros y transferencias, mientras gestiona cuentas y operaciones de manera eficiente. El proyecto implementa excepciones personalizadas para manejar escenarios de error específicos, garantizando una gestión robusta de errores.

---

## **Características**

### Operaciones en cuentas:
- Depositar fondos con conversión de divisas.
- Retirar fondos con validación de saldo.
- Transferir fondos entre cuentas con restricciones.

### Manejo de Excepciones Personalizadas:

- `SaldoMaximoSuperadoException`: Previene exceder el saldo máximo de la cuenta.
- `FondosInsuficientesException`: Garantiza que los retiros y transferencias estén dentro del saldo disponible.
- `CuentasIgualesException`: Evita transferencias a la misma cuenta.

### Gestión de Cuentas y Operaciones:
- Gestionar cuentas de usuarios con actualizaciones seguras de saldo.
- Listar y consultar depósitos y transferencias.
- Actualizar dinámicamente los detalles de las transferencias.

### Interfaz de Usuario Basada en Menús:
- Interfaz intuitiva basada en texto para la interacción del usuario.

## **Estructura del Proyecto**

```plaintext
Sistema de Gestión Bancaria/
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

## **Componentes Principales**

| Clase                  | Descripción                                                                             |
|------------------------|-----------------------------------------------------------------------------------------|
| `Banco`                | Clase central para gestionar cuentas, depósitos y funcionalidades de transferencias.    |
| `Cuenta`               | Representa cuentas bancarias individuales con número de cuenta y saldo.                 |
| `Operacion (Interfaz)` | Define la estructura para las operaciones bancarias (e.g., depósitos y transferencias). |
| `Deposito`             | Implementación de la interfaz Operacion para transacciones de depósito.                 |
| `Transferencia`        | Implementación de la interfaz Operacion para transferencias de fondos.                  |
| `Menu`                 | Proporciona la interfaz de usuario para interactuar con el sistema bancario.            |
---

## **Uso**

### 1. Clonar el repositorio:
```bash
git clone https://github.com/mcortezv/Banking-Management-System
```

### 2. Navegar al directorio del proyecto:
```bash
cd Banking-Management-System
```

### 3. Compilar el proyecto:
```bash
javac -Xlint:-options -source 8 -target 8 -d out src/main/java/bankingManagementSystem/*.java src/main/java/exceptions/*.java
```

### 4. Ejecutar la aplicación:
```bash
java -cp out bankingManagementSystem.Menu
```

## **Licencia**
Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](./LICENSE.md) para más detalles.