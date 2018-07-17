class CreateImages < ActiveRecord::Migration[5.2]
  def change
    create_table :images do |t|
    	t.attachment :extra_image
    	t.attachment :bathroom_image
    	t.attachment :kitchen_image
    	t.references :advertisments, foreign_key: true
    	t.timestamps
    end
  end
end
