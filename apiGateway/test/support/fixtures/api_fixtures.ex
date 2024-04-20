defmodule ApiGateway.ApiFixtures do
  @moduledoc """
  This module defines test helpers for creating
  entities via the `ApiGateway.Api` context.
  """

  @doc """
  Generate a gateway.
  """
  def gateway_fixture(attrs \\ %{}) do
    {:ok, gateway} =
      attrs
      |> Enum.into(%{
        method: "some method",
        name: "some name",
        url: "some url"
      })
      |> ApiGateway.Api.create_gateway()

    gateway
  end
end
