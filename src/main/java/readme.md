
src/
├── main/
│   └── AuthAPI.java          (Punto de entrada)
├── controllers/
│   └── AuthController.java   (Manejo de peticiones HTTP)
├── services/
│   └── AuthService.java      (Lógica de negocio)
├── repositories/
│   ├── UsuarioRepository.java (Acceso a datos usuarios)
│   └── TokenRepository.java   (Acceso a datos tokens)
├── models/
│   └── Usuario.java          (Entidad de dominio)
├── dto/
│   ├── RegistroRequest.java  (DTO para registro)
│   ├── LoginRequest.java     (DTO para login)
│   └── AuthResponse.java     (DTO para respuesta)
└── middleware/
└── AuthMiddleware.java   (Autenticación)
