class AddImageToAdver < ActiveRecord::Migration[5.2]
  def change
    add_reference :images, :advertisment, foreign_key: true
  end
end
