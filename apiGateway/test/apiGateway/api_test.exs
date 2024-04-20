defmodule ApiGateway.ApiTest do
  use ApiGateway.DataCase

  alias ApiGateway.Api

  describe "requests" do
    alias ApiGateway.Api.Gateway

    import ApiGateway.ApiFixtures

    @invalid_attrs %{name: nil, url: nil, method: nil}

    test "list_requests/0 returns all requests" do
      gateway = gateway_fixture()
      assert Api.list_requests() == [gateway]
    end

    test "get_gateway!/1 returns the gateway with given id" do
      gateway = gateway_fixture()
      assert Api.get_gateway!(gateway.id) == gateway
    end

    test "create_gateway/1 with valid data creates a gateway" do
      valid_attrs = %{name: "some name", url: "some url", method: "some method"}

      assert {:ok, %Gateway{} = gateway} = Api.create_gateway(valid_attrs)
      assert gateway.name == "some name"
      assert gateway.url == "some url"
      assert gateway.method == "some method"
    end

    test "create_gateway/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Api.create_gateway(@invalid_attrs)
    end

    test "update_gateway/2 with valid data updates the gateway" do
      gateway = gateway_fixture()
      update_attrs = %{name: "some updated name", url: "some updated url", method: "some updated method"}

      assert {:ok, %Gateway{} = gateway} = Api.update_gateway(gateway, update_attrs)
      assert gateway.name == "some updated name"
      assert gateway.url == "some updated url"
      assert gateway.method == "some updated method"
    end

    test "update_gateway/2 with invalid data returns error changeset" do
      gateway = gateway_fixture()
      assert {:error, %Ecto.Changeset{}} = Api.update_gateway(gateway, @invalid_attrs)
      assert gateway == Api.get_gateway!(gateway.id)
    end

    test "delete_gateway/1 deletes the gateway" do
      gateway = gateway_fixture()
      assert {:ok, %Gateway{}} = Api.delete_gateway(gateway)
      assert_raise Ecto.NoResultsError, fn -> Api.get_gateway!(gateway.id) end
    end

    test "change_gateway/1 returns a gateway changeset" do
      gateway = gateway_fixture()
      assert %Ecto.Changeset{} = Api.change_gateway(gateway)
    end
  end
end
