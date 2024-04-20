defmodule ApiGatewayWeb.Router do
  use ApiGatewayWeb, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/api", ApiGatewayWeb do
    pipe_through :api

    post "/get_all_orders", GatewayController, :get_all_orders
    post "/create_order", GatewayController, :create_order
    post "/cancel_order", GatewayController, :cancel_order
    get "/get_admin_products", GatewayController, :get_admin_products
    post "/add_product", GatewayController, :add_product
    get "/get_web_product", GatewayController, :get_web_product
    get "/get_all_web_products", GatewayController, :get_all_web_products
  end

  # Enable LiveDashboard and Swoosh mailbox preview in development
  if Application.compile_env(:apiGateway, :dev_routes) do
    # If you want to use the LiveDashboard in production, you should put
    # it behind authentication and allow only admins to access it.
    # If your application does not have an admins-only section yet,
    # you can use Plug.BasicAuth to set up some basic authentication
    # as long as you are also using SSL (which you should anyway).
    import Phoenix.LiveDashboard.Router

    scope "/dev" do
      pipe_through [:fetch_session, :protect_from_forgery]

      live_dashboard "/dashboard", metrics: ApiGatewayWeb.Telemetry
      forward "/mailbox", Plug.Swoosh.MailboxPreview
    end
  end
end
