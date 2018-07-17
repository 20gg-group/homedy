class CreateAdvertisments < ActiveRecord::Migration[5.2]
  def change
    create_table :advertisments do |t|    	
			t.string :title_post
			t.float :price
			t.boolean :status # da ban hay chua ban/ da co thue hoac chua
			t.references :users, foreign_key: true
    	t.timestamps
    end
  end
end
