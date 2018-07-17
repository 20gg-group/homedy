class AddAddressToAdver < ActiveRecord::Migration[5.2]
  def change
    add_reference :addresses, :advertisment, foreign_key: true
  end
end
