# Statify : A StatMuse inspired sports project

> A comprehensive, object-oriented sports statistics application functioning as a single source of truth for historical and current player and team data. This project serves as a robust demonstration of advanced Object-Oriented Programming (OOP) principles in a real-world data management scenario.

## Overview

Statify is a fully functional sports data querying platform inspired by StatMuse. It is designed with a dual-role architecture, allowing administrators to seamlessly manage complex sports databases while providing end-users with an intuitive, powerful interface to search, compare, and analyze athletic performance across multiple leagues.

## Core Features

### Admin Capabilities
*   **Centralized Data Management:** Full CRUD (Create, Read, Update, Delete) operations for core domain entities: Sports (e.g., NBA, NFL), Teams (e.g., Los Angeles Lakers), and Players (e.g., LeBron James).
*   **Dynamic Stats Entry:** Automated and manual data entry modules to upload player and team statistics immediately following a game or season.
*   **Database Integrity:** Encapsulated validation rules ensuring statistical accuracy before data is committed to the main repository.

### User Experience
*   **Comprehensive Directory:** Browse and filter data through categorized lists of sports, active/historical teams, and player rosters.
*   **Deep-Dive Analytics:** Select individual players to view complete career statistics, or isolate data sets by specific seasons and tournaments.
*   **Feedback Integration:** Direct channels for users to submit feedback or report data anomalies.

## Architecture & OOP Implementation

This system is built heavily on Object-Oriented Programming paradigms, making it highly modular and scalable:

*   **Encapsulation:** Player stats and administrative privileges are securely bundled within their respective classes, exposing only necessary methods to the user interface.
*   **Inheritance:** Base classes like `Sport` and `User` are extended into specific subclasses (e.g., `Basketball` inheriting from `Sport`; `Admin` and `Visitor` inheriting from `User`), reducing code duplication.
*   **Polymorphism:** Method overriding allows uniform functions like `displayStats()` to render entirely different formats depending on whether the object is an NFL quarterback or an MLB pitcher.
*   **Abstraction:** Complex database querying and statistical calculations are hidden behind simple interfaces, allowing the natural language search to function smoothly without exposing backend SQL/logic to the end-user.

## Installation & Setup

1. To run this you just need to clone my repository and run it in a Java based IDE. I personally recommend Intellij.
