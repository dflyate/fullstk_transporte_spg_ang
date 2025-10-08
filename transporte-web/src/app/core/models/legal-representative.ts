export interface LegalRepresentative {
    id: number;
    documentType: string;
    documentNumber: string;
    fullName: string;
    address: string;
    city: string;
    department: string; 
    country: string;
    phone: string;
    transportCompanyId: number;
}