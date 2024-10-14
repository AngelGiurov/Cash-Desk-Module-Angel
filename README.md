# Cash Desk Module

A Spring Boot application simulating cash operations (deposits and withdrawals) and allowing cashiers to check their cash balances and denominations. All operations are executed within the same working day.

## Features

- Deposit and withdraw money in BGN and EUR.
- Check balances and denominations after transactions.
- Transactions and balances are stored in TXT files.
- API secured with a custom API key header.
- Validates API requests using JSON format.

## Technologies Used

- Java 22
- Spring Boot
- Maven
- Embedded Tomcat
- JSON

## Prerequisites

- Java 22 installed
- Maven installed

## Installation and Running

1. **Clone the repository:**

    ```bash
    git clone https://github.com/AngelGiurov/Cash-Desk-Module-Angel.git
    cd cash-desk-module
    ```

2. **Build the project with Maven:**

    ```bash
    mvn clean install
    ```

3. **Run the application:**

    ```bash
    java -jar target/cash-desk-module-0.0.1-SNAPSHOT.jar
    ```

   The application will start on port **8080**.

## API Usage

### Custom Header

All API requests must include the custom header:

- **Must create environment variable `API_KEY` with value `f9Uie8nNf112hx8s`**.
- *Good practice for security measures but we have to test it somehow. 🙂*

- **Header Name:** `FIB-X-AUTH`
- **API Key:** `UPON REQUEST`

### Endpoints

#### 1. Cash Operation

- **URL:** `../api/v1/cash-operation`
- **Method:** `POST`
- **Description:** Handles both deposits and withdrawals.
- **Request Body Parameters:**
    - `operationType`: `"deposit"` or `"withdrawal"`
    - `currency`: `"BGN"` or `"EUR"`
    - `denominations`: A JSON object where keys are denomination values and values are quantities.
- **Request Body Example:**

    ```json
    {
      "operationType": "deposit",
      "currency": "BGN",
      "denominations": [
        {"value": 20, "count": 5},
        {"value": 50, "count": 2}
      ]
    }
    ```

#### 2. Cash Balance

- **URL:** `../api/v1/cash-balance`
- **Method:** `GET`
- **Description:** Returns current balances and denominations.
- **Response Example:**

    ```json
    {
       "balances": {
        "EUR": 2000,
        "BGN": 1000
    },
    "denominations": {
        "EUR": {
            "50": 20,
            "10": 100
        },
        "BGN": {
            "50": 10,
            "10": 50
        }
      }
    }
    ```

## Testing with Postman

A Postman collection with 5 requests is included in the repository under the `postman` directory.

1. **Import the Collection:**

    - Open Postman.
    - Click on **Import** and select the `CashDeskModule.postman_collection.json` file.

2. **Execute Requests in Order:**

    - The requests are numbered from 1 to 5.
    - Ensure the custom header `FIB-X-AUTH` is included with the API key `f9Uie8nNf112hx8s`.

## File Structure

- **Transaction History:** `transaction_history.txt`
- **Cash Balances and Denominations:** `cash_balances.txt`

These files are updated with each API call.