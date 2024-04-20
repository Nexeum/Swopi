defmodule ApiGatewayWeb.GatewayJSON do
  alias ApiGateway.Api.Gateway

  @doc """
  Renders a list of requests.
  """
  def index(%{requests: requests}) do
    %{data: for(gateway <- requests, do: data(gateway))}
  end

  @doc """
  Renders a single gateway.
  """
  def show(%{gateway: gateway}) do
    %{data: data(gateway)}
  end

  defp data(%Gateway{} = gateway) do
    %{
      id: gateway.id,
      name: gateway.name,
      url: gateway.url,
      method: gateway.method
    }
  end
end
