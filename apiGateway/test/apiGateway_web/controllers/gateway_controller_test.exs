defmodule ApiGatewayWeb.GatewayControllerTest do
  use ApiGatewayWeb.ConnCase

  import ApiGateway.ApiFixtures

  alias ApiGateway.Api.Gateway

  @create_attrs %{
    name: "some name",
    url: "some url",
    method: "some method"
  }
  @update_attrs %{
    name: "some updated name",
    url: "some updated url",
    method: "some updated method"
  }
  @invalid_attrs %{name: nil, url: nil, method: nil}

  setup %{conn: conn} do
    {:ok, conn: put_req_header(conn, "accept", "application/json")}
  end

  describe "index" do
    test "lists all requests", %{conn: conn} do
      conn = get(conn, ~p"/api/requests")
      assert json_response(conn, 200)["data"] == []
    end
  end

  describe "create gateway" do
    test "renders gateway when data is valid", %{conn: conn} do
      conn = post(conn, ~p"/api/requests", gateway: @create_attrs)
      assert %{"id" => id} = json_response(conn, 201)["data"]

      conn = get(conn, ~p"/api/requests/#{id}")

      assert %{
               "id" => ^id,
               "method" => "some method",
               "name" => "some name",
               "url" => "some url"
             } = json_response(conn, 200)["data"]
    end

    test "renders errors when data is invalid", %{conn: conn} do
      conn = post(conn, ~p"/api/requests", gateway: @invalid_attrs)
      assert json_response(conn, 422)["errors"] != %{}
    end
  end

  describe "update gateway" do
    setup [:create_gateway]

    test "renders gateway when data is valid", %{conn: conn, gateway: %Gateway{id: id} = gateway} do
      conn = put(conn, ~p"/api/requests/#{gateway}", gateway: @update_attrs)
      assert %{"id" => ^id} = json_response(conn, 200)["data"]

      conn = get(conn, ~p"/api/requests/#{id}")

      assert %{
               "id" => ^id,
               "method" => "some updated method",
               "name" => "some updated name",
               "url" => "some updated url"
             } = json_response(conn, 200)["data"]
    end

    test "renders errors when data is invalid", %{conn: conn, gateway: gateway} do
      conn = put(conn, ~p"/api/requests/#{gateway}", gateway: @invalid_attrs)
      assert json_response(conn, 422)["errors"] != %{}
    end
  end

  describe "delete gateway" do
    setup [:create_gateway]

    test "deletes chosen gateway", %{conn: conn, gateway: gateway} do
      conn = delete(conn, ~p"/api/requests/#{gateway}")
      assert response(conn, 204)

      assert_error_sent 404, fn ->
        get(conn, ~p"/api/requests/#{gateway}")
      end
    end
  end

  defp create_gateway(_) do
    gateway = gateway_fixture()
    %{gateway: gateway}
  end
end
