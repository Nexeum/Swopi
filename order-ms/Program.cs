using order_ms.service;
using order_ms.repository;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAllOrigins",
        builder =>
        {
            builder
            .AllowAnyOrigin()
            .AllowAnyMethod()
            .AllowAnyHeader();
        });
});

// Set up configuration
builder.Configuration.AddEnvironmentVariables(prefix: "ASPNETCORE_");

// Add services to the container.
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Add your services and repositories to the DI container
builder.Services.AddScoped<IOrderService, OrderService>();
builder.Services.AddScoped<ProductOrderRepository>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
    app.UseHttpsRedirection();
    app.UseCors("AllowAllOrigins");
}

app.MapControllers();

app.Run();