# 🏦 Enterprise Banking Information System (Prototype)

An industrial-grade, secure, and state-persistent console application engineered in **Core Java** during the Summer Internship Program. Facilitated by **upskill Campus** and **The IoT Academy** in collaboration with **UniConverge Technologies Pvt Ltd (UCT)**.

---

## 🚀 Repository Details & Quick Links
* **Repository Name:** `upskillcampus`
* **Core Source Engine:** [BankingInformationSystem.java](./src/com/upskill/Banking/BankingInformationSystem.java)
* **Official Evaluation Documentation:** [Download Internship Report (PDF)](./BankingInformationSystem_Mukul_USC_UCT.pdf)

---

## ⚡ Project Framework Overview
This banking engine bridges the gap between academic volatile structures and modern enterprise architectures by transitioning system data states from short-lived dynamic runtime arrays directly into hard-disk local persistence files. It includes structural bounds protection loops, multi-tier execution spaces, and credential privacy modules.

### 🔑 System Access Specifications
* **Administrative Operator Gateway:**
  * **Username:** `admin`
  * **System Password:** `admin123`
* **Customer Interface Access:** Dynamically auto-generated unique 10-Digit primary tokens distributed at the time of user registration.

---

## 🎨 System Architecture Flow (High-Level Mapping)

```text
  💎========================================================================💎
  ||             BANKING INFORMATION SYSTEM RUNTIME INTERFACE              ||
  💎========================================================================💎
                                       |
                                       v
                     [ 💾 AUTOMATED STORAGE ENGINE LAUNCH ]
                     [      Method: loadDataFromFile()    ]
                                       |
          +----------------------------+----------------------------+
          |                                                         |
          v                                                         v
  ╔══════════════════════════╗                               ╔══════════════════════════╗
  ║    1. VISITOR PORTAL     ║                               ║  2. ADMINISTRATOR CORE   ║
  ╚══════════════════════════╝                               ╚══════════════════════════╝
          |                                                         |
  +-------+-------+                                     +-----------+-----------+
  |               |                                     |                       |
  v               v                                     v                       v
[🆕 REGISTRATION] [🔐 SECURE LOGIN]             [📊 GLOBAL BRANCH AUDIT]  [🔍 PROFILE INVESTIGATOR]
  |               |                                     |                       |
  |-> Generate    |-> Linear Array                      |-> Loops database map  |-> Search 10-Digit ID
  |   10-Digit ID     Verification                      |   & tracks totals     |-> Mask plaintext pass
  |                                                     |                       |   via Obfuscation Layer
  v               v                                     v                       v
[📁 AUTO-SAVING] [👤 CUSTOMER PANEL]             [💰 VAULT ASSETS VALUE]   [🔒 CREDENTIAL STATE: HASHED]
          |               |                                     |                       |
          |               +---> [💵 DEPOSIT CAPITAL]            +-----------+-----------+
          |               +---> [💸 WITHDRAW ASSETS]                        |
          |               +---> [🔁 FUND TRANSFER  ]                        v
          |               +---> [📄 VIEW STATEMENT ]             [ 🔙 EXIT OPERATION CONTROL ]
          |                                                                 |
          +----------------------------+------------------------------------+
                                       |
                                       v
                     [ ⚙️ SYSTEM SYNCHRONIZATION BACKUP ]
                     [      Method: saveDataToFile()     ]
                                       |
                                       v
                     [ 🚀 STATE COMPLETED: DISK LOCKED ]
