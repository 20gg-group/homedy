class Image < ApplicationRecord
	belongs_to :advertisment, required: false

	# validates :extra_image, attachment_presence: true
	
  has_attached_file :extra_image, styles: { medium: "400x400>", thumb: "200x200>" }, default_url: "/images/error_picture.png"
  validates_attachment_content_type :extra_image, content_type: /\Aimage\/.*\z/
  validates_with AttachmentSizeValidator, attributes: :extra_image, less_than: 1.megabytes

  has_attached_file :bathroom_image, styles: { medium: "400x400>", thumb: "200x200>" }, default_url: "/images/error_picture.png"
  validates_attachment_content_type :bathroom_image, content_type: /\Aimage\/.*\z/
  validates_with AttachmentSizeValidator, attributes: :bathroom_image, less_than: 1.megabytes

	has_attached_file :kitchen_image, styles: { medium: "400x400>", thumb: "200x200>" }, default_url: "/images/error_picture.png"
  validates_attachment_content_type :kitchen_image, content_type: /\Aimage\/.*\z/
  validates_with AttachmentSizeValidator, attributes: :kitchen_image, less_than: 1.megabytes 
end
