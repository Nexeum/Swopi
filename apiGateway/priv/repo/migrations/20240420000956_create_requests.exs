defmodule ApiGateway.Repo.Migrations.CreateRequests do
  use Ecto.Migration

  def change do
    create table(:requests) do
      add :name, :string
      add :url, :string
      add :method, :string

      timestamps(type: :utc_datetime)
    end
  end
end
