import ParkingManagementService from "@/api/parking_management/ParkingManagementService";
import BookingService from "@/api/booking/BookingService";
import UserManagementService from "@/api/user_management/UserManagementService";
import LocationService from "@/api/location/LocationService";
import AnalyticsService from "@/api/analytics/AnalyticsService";
import PaymentService from "@/api/payment/PaymentService"


const ApiService = {
    user: UserManagementService,
    parking: ParkingManagementService,
    booking: BookingService,
    location: LocationService,
    analytics: AnalyticsService,
    payment: PaymentService
};

export default ApiService;
