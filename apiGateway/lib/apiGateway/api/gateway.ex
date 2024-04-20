defmodule ApiGateway.Api.Gateway do
  use Ecto.Schema
  import Ecto.Changeset

  schema "requests" do
    field :name, :string
    field :url, :string
    field :method, :string

    timestamps(type: :utc_datetime)
  end

  @doc false
  def changeset(gateway, attrs) do
    gateway
    |> cast(attrs, [:name, :url, :method])
    |> validate_required([:name, :url, :method])
  end
end
