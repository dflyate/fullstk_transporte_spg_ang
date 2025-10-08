import { LegalRepresentative } from "./legal-representative";
import { TransportCompany } from "./transport-company";
import { User } from "./user";

export interface TransportCompanyRegistration {
    transportCompany: TransportCompany,
    legalRepresentative: LegalRepresentative,
    user: User
}