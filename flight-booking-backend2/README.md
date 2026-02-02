
# Flight Booking Backend (Spring Boot)

Implements the endpoints expected by the React frontend guide, with JWT auth, CORS, and exception handling.

## Endpoints

### Auth
- `POST /api/admin/login` → `{ token }`
- `POST /api/users/login` → `{ token, user }`
- `POST /api/users/register` → `{ token, user }`

### Flights
- `GET /api/flights` → list
- `GET /api/flights/{id}` → item
- `POST /api/flights` *(ADMIN)* → create
- `PUT /api/flights/{id}` *(ADMIN)* → update
- `DELETE /api/flights/{id}` *(ADMIN)* → delete

### Bookings (requires user JWT)
- `POST /api/bookings` → create booking
- `GET /api/bookings/my-bookings` → list
- `GET /api/bookings/{id}` → detail (owner only)

## Run
1. Ensure MySQL running and update `application.properties` with credentials.
2. `mvn spring-boot:run`
3. Default admin credentials: `admin / admin123`.

## Notes
- JWT subject holds `userId` as string; claim `role` holds `USER` or `ADMIN`.
- CORS allows `http://localhost:3000` for `/api/**`.
