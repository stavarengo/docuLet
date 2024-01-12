# Doculet - Global Company Registration Document Normalization (CRD) System

> This project is part of my personal study journey in Java and Spring Boot, serving as a practical application of my learning.

## Introduction
Doculet is a RESTful API system designed to retrieve and normalize Company Registration Documents (CRDs) for organizations across the globe. Built using Java and Spring Boot.

## System Overview
Doculet encompasses the retrieval and normalization of Company Registration Documents (CRDs).

> In certain regions or contexts, the CRD may also be referred to as BRC (Business Registration Certificate) or CoC (Certificate of Conformity, Certificate of Compliance, or Certificate of Commerce).

### Key Features

#### Chain Retrieval Functionality & Data Normalization
- **Standardized Response Structure:** Doculet standardizes diverse document formats from various countries into a consistent response structure, ensuring uniformity across retrievals.
- **Recursive CRD Retrieval:** Doculet recursively retrieves CRD data when the organization is owned by other companies, effectively capturing the complete organizational structure.

#### RESTful API Endpoints
- **`POST /org`:** Create an organization record with country and Company Registration Document number. The client can choose to wait for the CRD to be readily available and returned in the same request or queue the CRD for asynchronous processing, receiving a webhook notification upon completion.
- **`PUT /org/{id}`:** Update details of existing organizations. Processing options are similar to the `POST /org` endpoint.
- **`GET /org/{id}`:** Retrieve the last available CRD for the specified organization.
- **`POST /org/{id}/refresh`:** Re-fetch the available CRD for the organization. Similar processing options to the `POST /org` endpoint.
- **`GET /orgs`:** Retrieve all organizations in the system with filtering, ordering, and pagination options.
- **`GET /org/{id}/history`:** Retrieve a list of all previously fetched CRDs for the specified organization, similar to the `GET /orgs` endpoint.
- **`GET /org/{id}/history/{country_code}-{crd_number})`:** Retrieve specific CRD data from the organization's history, providing options similar to the `GET /org/{id}` endpoint.

#### Security
- Token-based API access control.

### Functional Specifications
- **Domain Model:** Includes Organization, Document Data, and Change History entities. Countries are represented using two-letter codes based on the [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) standard.
- **Static Configuration:** Utilizes predefined list of authorized clients.
