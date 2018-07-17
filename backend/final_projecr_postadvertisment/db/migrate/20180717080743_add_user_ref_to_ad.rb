class AddUserRefToAd < ActiveRecord::Migration[5.2]
  def change
    add_reference :advertisments, :user, foreign_key: true
  end
end
