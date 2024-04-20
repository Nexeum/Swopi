defmodule ApiGatewayWeb.GatewayController do
  use ApiGatewayWeb, :controller

  alias ApiGateway.Api
  alias ApiGateway.Api.Gateway

  action_fallback ApiGatewayWeb.FallbackController

  def index(conn, _params) do
    requests = Api.list_requests()
    render(conn, :index, requests: requests)
  end

  def create(conn, %{"gateway" => gateway_params}) do
    with {:ok, %Gateway{} = gateway} <- Api.create_gateway(gateway_params) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", ~p"/api/requests/#{gateway}")
      |> render(:show, gateway: gateway)
    end
  end

  def show(conn, %{"id" => id}) do
    gateway = Api.get_gateway!(id)
    render(conn, :show, gateway: gateway)
  end

  def update(conn, %{"id" => id, "gateway" => gateway_params}) do
    gateway = Api.get_gateway!(id)

    with {:ok, %Gateway{} = gateway} <- Api.update_gateway(gateway, gateway_params) do
      render(conn, :show, gateway: gateway)
    end
  end

  def delete(conn, %{"id" => id}) do
    gateway = Api.get_gateway!(id)

    with {:ok, %Gateway{}} <- Api.delete_gateway(gateway) do
      send_resp(conn, :no_content, "")
    end
  end

  def get_all_orders(conn, params) do
    url = "http://localhost:5097/api/v1/order/get_all_orders"
    forward_request(conn, url, params, "POST")
  end

  def create_order(conn, params) do
    url = "http://localhost:5097/api/v1/order/create_order"
    forward_request(conn, url, params, "POST")
  end

  def cancel_order(conn, params) do
    url = "http://localhost:5097/api/v1/order/cancel_order"
    forward_request(conn, url, params, "POST")
  end

  def get_admin_products(conn, params) do
    url = "http://localhost:8081/api/v1/admin/products"
    forward_request(conn, url, params, "GET")
  end

  def add_product(conn, params) do
    url = "http://localhost:8081/api/v1/admin/products/add-product"
    forward_request(conn, url, params, "POST")
  end

  def get_web_product(conn, params) do
    url = "http://localhost:8081/api/v1/web/product"
    forward_request(conn, url, params, "GET")
  end

  def get_all_web_products(conn, params) do
    url = "http://localhost:8081/api/v1/web/product/all-products"
    forward_request(conn, url, params, "GET")
  end

  defp forward_request(conn, url, params, method) do
    case method do
      "GET" ->
        response = HTTPoison.get!(url)
        send_resp(conn, response.status_code, response.body)

      "POST" ->
        response = HTTPoison.post!(url, conn.params)
        send_resp(conn, response.status_code, response.body)

      _ ->
        send_resp(conn, 405, "Method not allowed")
    end
  end
end
